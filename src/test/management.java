package test;

import java.util.*;
import java.io.*;

public class management {
    private List<String> todoList = new ArrayList<>(); // 해야 할 일 목록
    private List<String> completedList = new ArrayList<>(); // 완료된 일 목록

    // 할 일 추가
    public void addTask(String task) {
        todoList.add(task);
        System.out.println("'" + task + "'이(가) 추가되었습니다.");
    }

    // 할 일 조회
    public void showTasks() {
        System.out.println("\n해야 할 일 목록:");
        if (todoList.isEmpty()) {
            System.out.println("현재 해야 할 일이 없습니다.");
        } else {
            for (int i = 0; i < todoList.size(); i++) {
                System.out.println((i + 1) + ". " + todoList.get(i));
            }
        }

        System.out.println("\n완료된 일 목록:");
        if (completedList.isEmpty()) {
            System.out.println("아직 완료된 일이 없습니다.");
        } else {
            for (int i = 0; i < completedList.size(); i++) {
                System.out.println((i + 1) + ". " + completedList.get(i));
            }
        }
    }

    // 할 일 완료
    public void completeTask(int taskIndex) {
        if (taskIndex < 1 || taskIndex > todoList.size()) {
            System.out.println("잘못된 번호입니다.");
            return;
        }
        String completedTask = todoList.remove(taskIndex - 1);
        completedList.add(completedTask);
        System.out.println("'" + completedTask + "'이(가) 완료되었습니다.");
    }

    // 파일에 저장
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("해야 할 일:\n");
            for (String task : todoList) {
                writer.write(task + "\n");
            }
            writer.write("완료된 일:\n");
            for (String task : completedList) {
                writer.write(task + "\n");
            }
            System.out.println("데이터가 '" + filename + "'에 저장되었습니다.");
        }
    }

    // 파일에서 불러오기
    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            todoList.clear();
            completedList.clear();

            String line;
            boolean isCompleted = false;

            while ((line = reader.readLine()) != null) {
                if (line.equals("해야 할 일:")) {
                    isCompleted = false;
                } else if (line.equals("완료된 일:")) {
                    isCompleted = true;
                } else if (!line.isEmpty()) {
                    if (isCompleted) {
                        completedList.add(line);
                    } else {
                        todoList.add(line);
                    }
                }
            }
            System.out.println("데이터가 '" + filename + "'에서 불러와졌습니다.");
        }
    }

    // 메인 함수
    public static void main(String[] args) {
        management todo = new management();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n[할 일 관리 프로그램]");
            System.out.println("1. 할 일 추가");
            System.out.println("2. 할 일 조회");
            System.out.println("3. 할 일 완료");
            System.out.println("4. 저장");
            System.out.println("5. 불러오기");
            System.out.println("6. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 줄바꿈 제거

            switch (choice) {
                case 1:
                    System.out.print("추가할 할 일: ");
                    String task = scanner.nextLine();
                    todo.addTask(task);
                    break;
                case 2:
                    todo.showTasks();
                    break;
                case 3:
                    todo.showTasks();
                    System.out.print("완료할 할 일 번호: ");
                    int taskIndex = scanner.nextInt();
                    todo.completeTask(taskIndex);
                    break;
                case 4:
                    System.out.print("저장할 파일 이름: ");
                    String saveFile = scanner.nextLine();
                    try {
                        todo.saveToFile(saveFile);
                    } catch (IOException e) {
                        System.out.println("파일 저장 중 오류가 발생했습니다.");
                    }
                    break;
                case 5:
                    System.out.print("불러올 파일 이름: ");
                    String loadFile = scanner.nextLine();
                    try {
                        todo.loadFromFile(loadFile);
                    } catch (IOException e) {
                        System.out.println("파일 불러오기 중 오류가 발생했습니다.");
                    }
                    break;
                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}

