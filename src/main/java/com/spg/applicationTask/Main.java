package com.spg.applicationTask;

import com.spg.applicationTask.api.controller.ProjectController;
import com.spg.applicationTask.api.controller.TaskController;
import com.spg.applicationTask.api.controller.UserController;
import com.spg.applicationTask.engine.IoC.Application;
import com.spg.applicationTask.engine.IoC.ApplicationContext;
import com.spg.applicationTask.engine.web.AbstractController;
import com.spg.applicationTask.engine.web.Server;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = Application.run(Main.class);

        Server server = context.getObject(Server.class);
        AbstractController projectController = context.getObject(ProjectController.class);
        AbstractController userController = context.getObject(UserController.class);
        AbstractController taskController = context.getObject(TaskController.class);

        server.addControllers(projectController, userController, taskController);
        server.start();
        System.out.println("To terminate the program press: q/Q ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            if ("q".equalsIgnoreCase(scanner.next())) {
                System.out.print("Termination of the program...");
                server.stop();
                Application.clean();
                return;
            } else {
                System.out.print("To terminate the program press: q/Q: ");
            }
        }
    }
}
