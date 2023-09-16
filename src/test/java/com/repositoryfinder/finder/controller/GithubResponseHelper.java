package com.repositoryfinder.finder.controller;

public interface GithubResponseHelper {


    default String bodyWithSingleRepositoryListForUser() {
        return """
                [
                      {
                          "name": "dog-shelter-friend",
                          "owner": {
                              "login": "user"
                          },
                          "fork": false,
                          "branches": [
                              {
                                  "name": "master",
                                  "commit": {
                                      "sha": "86b56a4b41c83c12eb030292723acfe48eb44393"
                                  }
                              },
                              {
                                  "name": "users2",
                                  "commit": {
                                      "sha": "b73151ba72f48b22615ece3f2de89a413d91e16f"
                                  }
                              }
                          ]
                      },
                      {
                          "name": "dog-shelter-friend--frontend-vaadin",
                          "owner": {
                              "login": "user"
                          },
                          "fork": false,
                          "branches": [
                              {
                                  "name": "master",
                                  "commit": {
                                      "sha": "86b56a4b41c83c12eb030292723acfe48eb44393"
                                  }
                              },
                              {
                                  "name": "users2",
                                  "commit": {
                                      "sha": "b73151ba72f48b22615ece3f2de89a413d91e16f"
                                  }
                              }
                          ]
                      }
                  ]
                """.trim();
    }

    default String bodyWithDog_shelter_friend() {
        return """
                [
                    {
                              "name": "master",
                              "commit": {
                                  "sha": "86b56a4b41c83c12eb030292723acfe48eb44393"
                              },
                              "protected": false
                          },
                          {
                              "name": "users2",
                              "commit": {
                                  "sha": "b73151ba72f48b22615ece3f2de89a413d91e16f"
                              },
                              "protected": false
                          }
                 ]
                """.trim();
    }


    default String bodyWithInvalidUsernameMessage() {
        return """
                {
                    "status": "NOT_FOUND",
                    "Message": "Resources not found for this user, probably bad username"
                }
                """.trim();
    }

    default String bodyWithRepositoriesForUser() {
        return """
                [
                     {
                         "name": "dog-shelter-friend",
                         "fork": false
                     },
                     {
                         "name": "dog-shelter-friend-frontend-vaadin",
                         "fork": false
                     },
                     {
                         "name": "dog-shelter-friend",
                         "fork": true
                     }
                ]
                """.trim();
    }

    default String bodyWithOneRepositoryForUser() {
        return """
                [
                     {
                         "name": "dog-shelter-friend",
                         "fork": false
                     }
                ]
                """.trim();
    }



    default String bodyWithOneSingleRepositoryForUser() {
        return """
                [
                     {
                          "name": "dog-shelter-friend",
                          "owner": {
                              "login": "user"
                          },
                          "fork": false,
                          "branches": [
                              {
                                  "name": "master",
                                  "commit": {
                                      "sha": "86b56a4b41c83c12eb030292723acfe48eb44393"
                                  }
                              },
                              {
                                  "name": "users2",
                                  "commit": {
                                      "sha": "b73151ba72f48b22615ece3f2de89a413d91e16f"
                                  }
                              }
                          ]
                      }
                  ]
                """.trim();
    }
}
