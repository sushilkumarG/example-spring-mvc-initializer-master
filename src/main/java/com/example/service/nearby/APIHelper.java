package com.example.service.nearby;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.nearby.NearbyProject;
import com.example.model.nearby.Project;

@Component
public class APIHelper {

    private static final Integer CLOSE_PROJECTS_TO_FETCH             = 20;
    private static final Integer DISTANCE_IN_KMS_FOR_NEARBY_PROJECTS = 10;

    public static final String   MIDL_URL                            = "https://www.proptiger.com/";

    public static final String   PROJECT_DETAIL_PARAMS               = "%d?selector={\"fields\":[\"latitude\",\"longitude\"]}";
    public static final String   NEARBY_UC_PROJECT_DETAIL_PARAMS     = "?selector={ \"fields\": [\"projectId\", \"name\", \"builder\", \"address\", \"name\", \"imageURL\", \"URL\", \"latitude\", \"longitude\"], \"filters\": { \"and\": [{ \"notEqual\": { \"projectId\": %d } }, { \"equal\": { \"projectStatus\": \"under construction\" } }, { \"geoDistance\": { \"geo\": { \"distance\": %d, \"lat\": %s, \"lon\": %s } } }] }, \"sort\": { \"field\": \"geoDistance\", \"sortOrder\": \"ASC\" }, \"paging\": { \"start\": 0, \"rows\": %d } } & debug = true";

    public static final String   PROJECT_DETAIL_V4_API               = "app/v4/project-detail/";
    public static final String   NEARBY_PROJECTS_API                 = "app/v1/project-listing";

    @Autowired
    private HttpRequestUtil      httpRequestUtil;

    public Project getProjectFromId(Integer projectId) {

        URI uri = URI.create(UriComponentsBuilder
                .fromUriString(MIDL_URL + PROJECT_DETAIL_V4_API + String.format(PROJECT_DETAIL_PARAMS, projectId))
                .build().encode().toString());
        Project project = null;
        try {
            project = httpRequestUtil.getInternalApiResultAsType(uri, null, Project.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    public List<NearbyProject> getNearbyProjects(Integer projectId, String latitude, String longitude) {

        URI uri = URI.create(UriComponentsBuilder
                .fromUriString(
                        MIDL_URL + NEARBY_PROJECTS_API
                                + String.format(
                                        NEARBY_UC_PROJECT_DETAIL_PARAMS,
                                        DISTANCE_IN_KMS_FOR_NEARBY_PROJECTS,
                                        projectId,
                                        latitude,
                                        longitude,
                                        CLOSE_PROJECTS_TO_FETCH)).build()
                .encode().toString());
        List<NearbyProject> projects = null;
        try {
            projects = httpRequestUtil.getInternalApiResultAsTypeList(uri, null, NearbyProject.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return projects;
    }
}
