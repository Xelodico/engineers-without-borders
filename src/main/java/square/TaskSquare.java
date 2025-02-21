package square;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Scanner;

import BoardGame.*;
import GameSystem.*;


public class TaskSquare extends Square {

    /**
     * The task associated with this square, which players can attempt to complete.
     */
    private Task task;
    private final SquareType sType = SquareType.TASKSQUARE;
    private final Color squareColor = Color.RED;

    /**
     * Constructs a TaskSquare object with the specified task.
     * This task will be available for players who land on this square.
     * Initializes the squareColor to Color.RED and sType to SquareType.TASKSQUARE.
     *
     * @param ts The task associated with this square.
     */
    public TaskSquare(Task ts) {
        super();
        this.task = ts;
    }

    /**
     * When a player lands on a TaskSquare, if it is not claimed by another player,
     * they have the opportunity to claim it.
     * If the player doesn't wish to claim this task, they can choose to pass it to
     * another player or leave it unclaimed.
     * However, if the task is already claimed by another player, the current player
     * can choose to help complete the task.
     * 
     * @return {@code true} if the task was successfully activated, {@code false}
     *         otherwise.
     */
    @Override
    public boolean activateSquareEffect() {

        ActionListener takeTask = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Take task logic
                task.setOwnedBy(getPrimaryOccupier());
                System.out.println("Task claimed!");
                GameSystem.hidePopup();
            }
        };

        ActionListener rejectTask = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Reject task logic
                System.out.println("Show the task to other players");
                GameSystem.hidePopup();
            }
        };

        ActionListener beginHelping = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // task.getOwnedBy().findTask(task).currentSubTask().setDiscounted(true);
                // task.currentSubTask().setDiscounted(true);
                System.out.println("Discounting the task!");
                GameSystem.hidePopup();
            }
        };

        ActionListener ignoreHelping = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameSystem.hidePopup();
            }
        };

        ActionListener okSingleButton = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameSystem.hidePopup();
            }
        };

        super.activateSquareEffect();
        if(task.getOwnedBy() == null) {
            GameSystem.showPopup("Do you want to get this task?", "", "Yes", "No", takeTask, rejectTask);
            // System.out.println("Do you want to get this task?");
            // Scanner keyb = new Scanner(System.in);
            // String input = keyb.nextLine();
            // if("y".equals(input)) {
            //     task.setOwnedBy(getPrimaryOccupier());
            //     System.out.println("Task claimed!");
            // } else {
            //     System.out.println("Who wants this task?");
            //     input = keyb.nextLine();
            //     switch (input) {
            //         case "1":
            //             GameSystem.getPlayerAt(0).addTask(task);
            //             break;
            //         case "2":
            //             GameSystem.getPlayerAt(1).addTask(task);
            //             break;
            //         case "3":
            //             GameSystem.getPlayerAt(2).addTask(task);
            //             break;
            //         case "4":
            //             GameSystem.getPlayerAt(3).addTask(task);
            //             break;
            //         default:
            //             System.out.println("Nobody claimed the task, -15 satisfaction points");
            //             break;
            //     }
            // }
            // keyb.close();
        } else if (task.getOwnedBy() != getPrimaryOccupier()) {
            GameSystem.showPopup("Do you want to help complete this task?", task.getDescription(), "Yes", "No", beginHelping, ignoreHelping);
            // System.out.println("Do you want to help complete this task?");
            // Scanner keyb = new Scanner(System.in);
            // String input = keyb.nextLine();
            // if("y".equals(input)) {
            //     // Discount the task.
            //     System.out.println("Discounting the task!");
            // }
            // // keyb.close();
        } else {
            GameSystem.showPopup("You already own this task!", null, "Ok", null, okSingleButton, null);
            // System.out.println("You already own this task!");
        }
        return true;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public SquareType getSquareType() {
        return this.sType;
    }

    public Color getColor() {
        return this.squareColor;
    }
}
