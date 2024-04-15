CREATE TABLE oauth_client_details (
      client_id VARCHAR(256) PRIMARY KEY,
      resource_ids VARCHAR(256),
      client_secret VARCHAR(256),
      scope VARCHAR(256),
      authorized_grant_types VARCHAR(256),
      web_server_redirect_uri VARCHAR(256),
      authorities VARCHAR(256),
      access_token_validity INT,
      refresh_token_validity INT,
      additional_information VARCHAR(4096),
      autoapprove VARCHAR(256)
);

CREATE TABLE oauth_client_token (
    token_id VARCHAR(256),
    token MEDIUMBLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name VARCHAR(256),
    client_id VARCHAR(256)
);

CREATE TABLE oauth_access_token (
    token_id VARCHAR(256),
    token MEDIUMBLOB,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name VARCHAR(256),
    client_id VARCHAR(256),
    authentication MEDIUMBLOB,
    refresh_token VARCHAR(256)
);

CREATE TABLE oauth_refresh_token (
    token_id VARCHAR(256),
    token MEDIUMBLOB,
    authentication MEDIUMBLOB
);

CREATE TABLE oauth_code (
    code VARCHAR(256),
    authentication MEDIUMBLOB
);

CREATE TABLE oauth_approvals (
     userId VARCHAR(256),
     clientId VARCHAR(256),
     scope VARCHAR(256),
     status VARCHAR(10),
     expiresAt DATETIME,
     lastModifiedAt DATETIME
);

CREATE TABLE ClientDetails (
       appId VARCHAR(256) PRIMARY KEY,
       resourceIds VARCHAR(256),
       appSecret VARCHAR(256),
       scope VARCHAR(256),
       grantTypes VARCHAR(256),
       redirectUrl VARCHAR(256),
       authorities VARCHAR(256),
       access_token_validity INT,
       refresh_token_validity INT,
       additionalInformation VARCHAR(4096),
       autoApproveScopes VARCHAR(256)
);

INSERT INTO testmysql.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('clientId', null, '{noop}secretKey', 'read,write', 'authorization_code,password,refresh_token,client_credentials', 'http://localhost:7070/callback', 'GENERAL', 60, 3600, null, 'true');

INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove
) VALUES ( 'login-client',
             NULL, -- resource_ids
             '{noop}openid-connect', -- client_secret (noop은 평문)
             'openid,profile', -- scope
             'authorization_code', -- authorized_grant_types
             'http://127.0.0.1:7070/login/oauth2/code/login-client', -- web_server_redirect_uri
             NULL, -- authorities
             3600, -- access_token_validity (초 단위)
             3600, -- refresh_token_validity (초 단위)
             NULL, -- additional_information NULL -- autoapprove
         );