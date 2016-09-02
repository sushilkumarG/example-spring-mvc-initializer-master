package com.example.service.nearby;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.nearby.Location;
import com.example.model.nearby.NearbyProject;
import com.example.model.nearby.Project;

@Service
public class ProjectService {

    @Autowired
    private APIHelper apiHelper;

    public Location getLocation(Integer projectId) {
        Project p = apiHelper.getProjectFromId(projectId);
        return new Location(String.valueOf(p.getLatitude()), String.valueOf(p.getLongitude()));
    }

    public List<NearbyProject> getNearbyProjects(Integer projectId) {
        Project p = apiHelper.getProjectFromId(projectId);
        return apiHelper.getNearbyProjects(projectId, p.getLatitude(), p.getLongitude());
    }

}
