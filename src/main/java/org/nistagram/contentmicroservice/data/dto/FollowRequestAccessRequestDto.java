package org.nistagram.contentmicroservice.data.dto;

public class FollowRequestAccessRequestDto {
    private String follower;
    private String followee;

    public FollowRequestAccessRequestDto() {
    }

    public FollowRequestAccessRequestDto(String follower, String followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }
}
