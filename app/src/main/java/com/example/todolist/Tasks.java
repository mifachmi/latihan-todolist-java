package com.example.todolist;

import android.widget.CheckBox;

public class Tasks {
    public String[] nameTask;
//    public CheckBox checkBox;
    public String[] descriptionTask;
    public String[] dueDateTask;

//    public Tasks(String nameTask, String descriptionTask, String dueDateTask) {
//        this.nameTask = nameTask;
//        this.descriptionTask = descriptionTask;
//        this.dueDateTask = dueDateTask;
//    }

    public Tasks(String[] dataTask, String[] desc, String[] dueDate) {
        this.nameTask = dataTask;
        this.descriptionTask = desc;
        this.dueDateTask = dueDate;
    }

//    public String getNameTask() {
//        return nameTask;
//    }
//
//    public void setNameTask(String nameTask) {
//        this.nameTask = nameTask;
//    }
//
//    public CheckBox getCheckBox() {
//        return checkBox;
//    }
//
//    public void setCheckBox(CheckBox checkBox) {
//        this.checkBox = checkBox;
//    }
//
//    public String getDescriptionTask() {
//        return descriptionTask;
//    }
//
//    public void setDescriptionTask(String descriptionTask) {
//        this.descriptionTask = descriptionTask;
//    }
//
//    public String getDueDateTask() {
//        return dueDateTask;
//    }
//
//    public void setDueDateTask(String dueDateTask) {
//        this.dueDateTask = dueDateTask;
//    }
}

