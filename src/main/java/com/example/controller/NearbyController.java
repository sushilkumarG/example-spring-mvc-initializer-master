package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.nearby.Location;
import com.example.model.nearby.NearbyProject;
import com.example.service.nearby.ProjectService;

@Controller
@RequestMapping(value = "/")
class NearbyController {

    @Autowired
    private ProjectService projectService;

    @ResponseBody
    public String showIndex(@RequestParam String latitude, @RequestParam String longitude) {
        return "Hello world" + latitude + longitude;
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    @ResponseBody
    public Location findAmenitiesNearby(@RequestParam Integer projectId) {
        return projectService.getLocation(projectId);
    }

    @RequestMapping(value = "/nearby-projects", method = RequestMethod.GET)
    @ResponseBody
    public List<NearbyProject> getNearbyProjects(Integer projectId) {
        return projectService.getNearbyProjects(projectId);
    }
}
