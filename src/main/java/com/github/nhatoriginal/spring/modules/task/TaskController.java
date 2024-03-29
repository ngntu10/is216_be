package com.github.nhatoriginal.spring.modules.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @GetMapping
  public Iterable<Task> getTaskList() {
    return taskService.getTaskList();
  }

  @GetMapping("/{id}")
  public Task getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id);
  }
}