package org.launchcode.codingevents2.controllers;

import org.launchcode.codingevents2.data.EventData;
import org.launchcode.codingevents2.models.Event;
import org.launchcode.codingevents2.models.EventType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", EventData.getAll());
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("types", EventType.values());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {

        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            return "events/create";
        }
        EventData.add(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventForm(@RequestParam(required = false) int[] eventIds) {

        if(eventIds != null) {
            for(int id : eventIds) {
                EventData.remove(id);
            }
        }

        return "redirect:";

    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {
        Event selectedEvent = EventData.getById(eventId);
        model.addAttribute("selectedEvent", selectedEvent);
        String title =  "Edit Event " + selectedEvent.getName() +" (ID=" + selectedEvent.getId() +")";
        model.addAttribute("title", title);
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description) {
        Event selectedEvent = EventData.getById(eventId);
        selectedEvent.setName(name);
        selectedEvent.setDescription(description);
        return "redirect:";
    }

}