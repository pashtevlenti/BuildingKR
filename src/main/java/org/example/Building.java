package org.example;

public class Building implements StageInterface, BuildingInterface {

    private Node head;

    public Building() {

        Node current = new Node(new Project(StageStatus.запланирован, "Проект"));
        head = current;
        current.next = new Node(new Foundation(StageStatus.запланирован, "Фундамент"));
        current.next.prev = current;
        current = current.next;

        current.next = new Node(new Walls(StageStatus.запланирован, "Стены"));
        current.next.prev = current;
        current = current.next;

        current.next = new Node(new Roof(StageStatus.запланирован, "Крыша"));
        current.next.prev = current;
        current = current.next;

        current.next = new Node(new Finishing(StageStatus.запланирован, "Отделка"));
        current.next.prev = current;

        current.next.next = head;
        head.prev = current.next;
    }

    @Override
    public Stage next() {
        head = head.next;
        return head.stage;
    }

    @Override
    public Stage prev() {
        head = head.prev;
        return head.stage;
    }

    @Override
    public void start() {
        try {
            int x = ((int) (Math.random() * 4));
            if (x == 0) throw new ProjectRejectedException();
            System.out.println("Стройка началась");

        } catch (ProjectRejectedException e) {
            System.out.println("Проект забракован, стройка не состоялась");
            head.stage.setStatus(StageStatus.забракован);
        }
    }

    @Override
    public void finish() {
        Node start = head;
        if (start.stage.getStatus() == StageStatus.запланирован) {
            Stage currentStage = head.stage;
            while (start.prev.stage.getStatus() != StageStatus.выполнен) {
                try {
                    int x = ((int) (Math.random() * 4));
                    if (x == 0) {
                        switch (currentStage.getName()) {
                            case "Проект":
                                throw new ProjectRejectedException();
                            case "Фундамент":
                                throw new FoundationRejectedException();
                            case "Стены":
                                throw new WallsRejectedException();
                            case "Крыша":
                                throw new RoofRejectedException();
                            case "Отделка":
                                throw new FinishingRejectedException();
                        }
                    }
                    currentStage.setStatus(StageStatus.запланирован);
                    System.out.println("Этап " + currentStage.getName() + " " + currentStage.getStatus());
                    currentStage.setStatus(StageStatus.осуществляется);
                    System.out.println("Этап " + currentStage.getName() + " " + currentStage.getStatus());
                    currentStage.setStatus(StageStatus.выполнен);
                    System.out.println("Этап " + currentStage.getName() + " " + currentStage.getStatus());

                    currentStage = next();
                } catch (ProjectRejectedException e) {
                    currentStage.setStatus(StageStatus.забракован);
                    System.out.println("В конечном итоге проект был " + currentStage.getStatus() + ", и стройка не состоялась");
                    break;
                } catch (FoundationRejectedException | WallsRejectedException | RoofRejectedException |
                         FinishingRejectedException e) {
                    currentStage.setStatus(StageStatus.забракован);
                    System.out.println("Этап стройки " + currentStage.getName() + " был " + currentStage.getStatus() + ", а стройка вернулась на предыдущий этап");

                    currentStage = prev();
                }
            }
            if (start.prev.stage.getStatus() == StageStatus.выполнен) {
                System.out.println("Стройка успешно завершилась");
            }
        }

    }
}
