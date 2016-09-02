package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Value;
import com.example.service.WebSocketMessanger;

@Controller
@RequestMapping(value = "/index-websocket")
class IndexController {

    @Autowired
    private WebSocketMessanger webSocketMessanger;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String showIndex() {
        return "Hello world";
    }

    @RequestMapping(value = "hi/{x}", method = RequestMethod.GET)
    @ResponseBody
    public String getMessage(@PathVariable String x, @RequestParam Integer id) {
        webSocketMessanger.sendConfCallMessage(x, id);
        return "Message sent to" + x;

    }

    @MessageMapping("/preconfcall/{id}")
    @SendTo("/queue/confcallrunning")
    public Value chat(@DestinationVariable Integer id) {
        webSocketMessanger.sendConfCallMessage("confcallrunning", id);
        return new Value(id.toString());

    }
}
