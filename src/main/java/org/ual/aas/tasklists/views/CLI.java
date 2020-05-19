package org.ual.aas.tasklists.views;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static org.ual.aas.tasklists.App.printTaskList;
import org.ual.aas.tasklists.HibernateUtil;
import org.ual.aas.tasklists.controllers.TaskController;
import org.ual.aas.tasklists.controllers.TaskListController;
import org.ual.aas.tasklists.models.TaskList;


public class CLI {
    
    public void menuView() {
          
        while(true) {
            System.out.println("Command Options: ");
            System.out.println("1: Opções para lista de listas.");
            System.out.println("2: Opções para lista de tarefas.");
            System.out.println("3: Opções para tarefas.");
            System.out.println("4: Quit");
            System.out.print("Your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1: // LISTA DE LISTAS
                    System.out.println("Command Options: ");
                    System.out.println("1: GET (obter listagem de listas)");
                    System.out.println("2: POST (criar nova lista)");
                    System.out.println("3: Quit");
                    System.out.print("Your choice: ");
                    int choice2 = scanner.nextInt();
                    
                    switch (choice2) {
                        case 1:
                            System.out.println("=====================================================================");
                            TaskListController.getTaskListsL();
                            System.out.println("=====================================================================");
                            break;
                        case 2:
                            System.out.println("=====================================================================");
                            System.out.print("Qual o nome para a lista?: ");
                            String name = scanner.next();
                            TaskListController.insertTaskLists(name);
                            System.out.println("A lista " + name + " foi adicionada com sucesso.");
                            System.out.println("=====================================================================");
                            break;
                        case 3:
                            System.out.println("=====================================================================");
                            System.out.println("Exit.");
                            System.out.println("=====================================================================");
                            break;
                    }
                    break;
                case 2: // LISTA DE TAREFAS
                    System.out.println("Command Options: ");
                    System.out.println("1: GET (obter lista, incluindo tarefas)");
                    System.out.println("2: PUT (alterar nome da lista)");
                    System.out.println("3: POST (adiciona uma tarefa à lista)");
                    System.out.println("4: DELETE (elimina a lista e as suas tarefas)");
                    System.out.println("5: Quit");
                    System.out.print("Your choice: ");
                    choice2 = scanner.nextInt();
                    
                    switch (choice2) {
                        case 1:
                            System.out.print("Qual o id da lista que quer ver?: ");
                            int id = scanner.nextInt();
                            TaskListController.getTaskLists(id);
                            break;
                        case 2:
                            System.out.print("Qual o id da lista que quer modificar?: ");
                            id = scanner.nextInt();
                            System.out.print("Qual o nome que quer por?: ");
                            String name = scanner.next();
                            TaskListController.updateTaskLists(id, name);
                            break;
                        case 3:
                            System.out.print("Quer adicionar uma tarefa a que lista ?: ");
                            id = scanner.nextInt();
                            System.out.print("Qual a descriçao da tarefa?: ");
                            name = scanner.next();
                            TaskController.insertTasks(id, name);
                            break;
                        case 4:
                            System.out.print("Quer apagar que lista ?: ");
                            id = scanner.nextInt();
                            TaskListController.deleteTaskLists(id);
                            break;
                        case 5:
                            System.out.println("Exit.");
                    }
                    break; 
                case 3: // TAREFAS
                    System.out.println("Command Options: ");
                    System.out.println("1: GET (obter tarefas)");
                    System.out.println("2: PUT (alterar descriçao da tarefa)");
                    System.out.println("3: POST (altera o estado da tarefa)");
                    System.out.println("4: DELETE (elimina a tarefa)");
                    System.out.println("5: Quit");
                    System.out.print("Your choice: ");
                    choice2 = scanner.nextInt();
                    
                    switch (choice2) {
                        case 1:
                            System.out.print("Qual o id da tarefa que quer ver?: ");
                            int id = scanner.nextInt();
                            TaskController.getTasks(id);
                            break;
                        case 2:
                            System.out.print("Qual o id da tarefa que quer modificar?: ");
                            id = scanner.nextInt();
                            System.out.print("Qual o nome que quer por?: ");
                            String name = scanner.next();
                            TaskController.updateNameTasks(id, name);
                            break;
                        case 3:
                            System.out.print("Qual o id da tarefa que quer modificar?: ");
                            id = scanner.nextInt();
                            System.out.print("Qual o status que quer por?: ");
                            name = scanner.next();
                            TaskController.updateStatusTasks(id, name);
                            break;
                        case 4:
                            System.out.print("Quer apagar que tarefa ?: ");
                            id = scanner.nextInt();
                            TaskController.deleteTasks(id);
                            break;
                        case 5:
                            System.out.println("Exit.");
                    }
                    break;
                    //TaskController.insertTasks(1);                  
                case 4:     
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong number dumbass");
                    // The user input an unexpected choice.
            }
        }
        
    }
    
        public void createDatabase() { // NAO ESTA A FAZER NADA, MAS VOU DEIXAR CA POR ENQUANTO
           
            Transaction transaction = null;
            try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                   transaction.commit();
                   session.close();

            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }

            try ( Session session = HibernateUtil.getSessionFactory().openSession()) {
                List resultSet = session.createQuery("from TaskList").list();
                for (TaskList tl : (List<TaskList>) resultSet) {
                    printTaskList(tl);
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
    }
 }
    
    
    

