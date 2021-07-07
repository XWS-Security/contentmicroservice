package org.nistagram.contentmicroservice.service.impl;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.nistagram.contentmicroservice.data.dto.ContentMessageInfoDto;
import org.nistagram.contentmicroservice.data.dto.FollowRequestAccessResponseDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.model.content.Story;
import org.nistagram.contentmicroservice.data.repository.ContentRepository;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.exceptions.AccessToUserProfileDeniedException;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Service
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;
    private final NistagramUserRepository nistagramUserRepository;

    @Value("${FOLLOWER}")
    private String followerMicroserviceURI;

    public ContentServiceImpl(ContentRepository contentRepository, NistagramUserRepository nistagramUserRepository) {
        this.contentRepository = contentRepository;
        this.nistagramUserRepository = nistagramUserRepository;
    }

    @Override
    public ContentMessageInfoDto getContentInfo(long id, String token) throws SSLException {
        var content = findById(id);
        var owner = nistagramUserRepository.findByContentContaining(id);
        boolean hasAccess = this.isAccessAllowed(owner, token);

        var type = "";
        var contentPath = "";
        if (content instanceof Post) {
            type = "POST";
            contentPath = hasAccess ? ((Post) content).getPaths()[0] : "";
        } else if (content instanceof Story) {
            type = "STORY";
            contentPath = hasAccess ? ((Story) content).getPath() : "";
        }
        return new ContentMessageInfoDto(owner.getProfilePictureName(), owner.getUsername(), type, hasAccess, contentPath);
    }

    private Content findById(long id) {
        var optional = contentRepository.findById(id);
        if (optional.isEmpty()) throw new NotFoundException();
        return optional.get();
    }

    private boolean isAccessAllowed(NistagramUser owner, String token) throws AccessToUserProfileDeniedException, SSLException {
        NistagramUser user = getCurrentlyLoggedUser();

        if (!owner.getProfilePrivate()) {
            return true;
        }
        if (owner.getUsername().equals(user.getUsername())) {
            return true;
        }

        String followee = owner.getUsername();

        // SSL
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        // Creating web client.
        WebClient client = WebClient.builder()
                .baseUrl(followerMicroserviceURI)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        // Define a method.
        var result = client.get()
                .uri("/users/hasAccess/" + followee)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(FollowRequestAccessResponseDto.class)
                .block();

        return result != null && result.isAccessAllowed();
    }

    private NistagramUser getCurrentlyLoggedUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            return null;
        } else {
            return (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }
}
