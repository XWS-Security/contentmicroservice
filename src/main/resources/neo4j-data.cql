CREATE (p:Privilege {name: 'NISTAGRAM_USER_PRIVILEGE' })
CREATE (r:Role {name: 'NISTAGRAM_USER_ROLE'})

MATCH (r:Role {name: 'NISTAGRAM_USER_ROLE'}), (p:Privilege {name: 'NISTAGRAM_USER_PRIVILEGE'}) CREATE (r)-[:HAS_PRIVILEGE]->(p)

CREATE (u:AuthUser {email:'pharmacyisa6+luka@gmail.com', password:'$2a$10$/b6nrwDAIMHQv/wAeD004u91l/k.973ksiTVbL1yJCKw3TuVaHMf6', enabled: true, nistagramUsername: 'luka', lastPasswordResetDate: datetime('2017-10-01 21:58:58.508-07')})

MATCH (u:AuthUser {nistagramUsername: 'luka'}), (r:Role {name: 'NISTAGRAM_USER_ROLE'}) CREATE (u)-[:HASROLE]->(r)