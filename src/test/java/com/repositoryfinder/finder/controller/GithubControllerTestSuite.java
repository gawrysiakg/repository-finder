package com.repositoryfinder.finder.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositoryfinder.finder.domain.model.RepositoryProperty;
import com.repositoryfinder.finder.domain.model.SingleRepository;
import com.repositoryfinder.finder.domain.service.GithubService;
import com.repositoryfinder.finder.infrastructure.apivalidation.ApiValidationErrorResponseDto;
import com.repositoryfinder.finder.infrastructure.controller.GithubController;
import com.repositoryfinder.finder.infrastructure.dto.GithubResponseDto;
import com.repositoryfinder.finder.infrastructure.error.ErrorUserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(GithubController.class)
public class GithubControllerTestSuite implements GithubResponseHelper{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GithubService githubService;
    @Autowired
    public ObjectMapper objectMapper;


    @Test
    public void test_get_all_repositories_by_username() throws Exception {
        // given
        List<RepositoryProperty> repoNames = objectMapper.readValue(bodyWithRepositoriesForUser(), new TypeReference<>() {});
        List<SingleRepository> list = objectMapper.readValue(bodyWithSingleRepositoryListForUser(), new TypeReference<>() {});
        when(githubService.getNotForkedRepositoryNamesForUser("user")).thenReturn(repoNames);
        when(githubService.getAllReposWithBranches("user")).thenReturn(list);

        // when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080")
                .content("""
                        {
                        "username": "user"
                        }
                        """.trim())
                .accept("application/json" )
                .header("Accept", "application/json" )
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResult = perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].repositoryName").value("dog-shelter-friend"))
                .andExpect(jsonPath("$[0].ownerLogin").value("user" ))
                .andExpect(jsonPath("$[0].branches[0].branchName").value("master" ))
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").value("86b56a4b41c83c12eb030292723acfe48eb44393" ))
                .andReturn();
        String jsonWithResponse = mvcResult.getResponse().getContentAsString();
        List<GithubResponseDto> listGithubResponseDto = objectMapper.readValue(jsonWithResponse, new TypeReference<>() {
        });
        assertThat(listGithubResponseDto).hasSize(2);
        assertEquals("dog-shelter-friend", listGithubResponseDto.get(0).repositoryName());
        assertEquals("user", listGithubResponseDto.get(0).ownerLogin());
        assertEquals("master", listGithubResponseDto.get(0).branches().get(0).branchName());
        assertEquals("86b56a4b41c83c12eb030292723acfe48eb44393", listGithubResponseDto.get(0).branches().get(0).lastCommitSha());
    }

    @Test
    public void test_get_all_repositories_by_username_with_header_application_xml() throws Exception {

        // given & when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080")
                .content("""
                        {
                        "username": "user"
                        }
                        """.trim())
                .accept(MediaType.APPLICATION_XML)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(406))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_ACCEPTABLE.name()))
                .andExpect(jsonPath("$.Message").value("xml is not acceptable media type, only application/json"));
        // then
        MvcResult mvcResult = perform.andReturn();
        String jsonWithResponse = mvcResult.getResponse().getContentAsString();
        ErrorUserResponseDto errorDto = objectMapper.readValue(jsonWithResponse, new TypeReference<>() {});
        assertEquals(HttpStatus.NOT_ACCEPTABLE, errorDto.status());
        assertEquals("xml is not acceptable media type, only application/json", errorDto.Message());
    }

    @Test
    public void test_get_all_repositories_by_username_without_username() throws Exception {

        // given & when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080")
                        .content("""
                        {
                        
                        }
                        """.trim())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                //.andExpect(jsonPath("$.errors[0]").value("Username can not be empty"))
                //.andExpect(jsonPath("$.errors[1]").value("Username can not be null"))
                .andExpect(jsonPath("$.errors", hasItems("Username can not be empty", "Username can not be null")))
                .andExpect(jsonPath("$.httpStatus").value(HttpStatus.BAD_REQUEST.name()));
        // then
        MvcResult mvcResult = perform.andReturn();
        String jsonWithResponse = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorResponseDto validationDto = objectMapper.readValue(jsonWithResponse, new TypeReference<>() {});
        assertEquals(HttpStatus.BAD_REQUEST, validationDto.httpStatus());
        assertEquals(2, validationDto.errors().size());
    }

}
