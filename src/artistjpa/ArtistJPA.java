
package artistjpa;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class ArtistJPA {

    
    static boolean loop = true;
    static Scanner scanner = new Scanner(System.in);
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");

    public static void main(String[] args) {
        
                while(loop) {
            
            switchChoice(menu());
        }
        
    }
    
        public static int menu() {
       
        System.out.println("1. Add Artist");
        System.out.println("2. Update Artist");
        System.out.println("3. Delete Artist");
        System.out.println("4. Show Artist by id");
        System.out.println("5. Show Artist by first name");
        System.out.println("6. Show Artist by last name");
        System.out.println("7. Show all");
        System.out.println("0. Exit");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
        
    }
    
    public static void switchChoice(int choice) {
        
        switch(choice) {
            
            case 0:
                loop = false;
                break;
            case 1:
                addArtist();
                break;
            case 2:
                updateArtist();
                break;
            case 3:
                deleteArtist();
                break;
            case 4:
                showArtistById();
                break;
            case 5:
                showArtistByFirstName();
                break;
            case 6:
                showArtistByLastName();
            case 7:
                showAll();
                break;
            default:
                System.out.println("No such option, try again.");
                break;     
        }
    }

    public static void addArtist() {
        
        EntityManager em = emf.createEntityManager();
        
        System.out.println("First name:");
        String firstname = scanner.nextLine();
        
        System.out.println("Last name:");
        String lastname = scanner.nextLine();
        
        System.out.println("Age:");
        int age = scanner.nextInt();
        scanner.nextLine();
        
        em.getTransaction().begin();
        em.persist(new Artist(firstname, lastname, age));
        em.getTransaction().commit();
        
        em.close();
    }

    public static void updateArtist() {
        
        EntityManager em = emf.createEntityManager();
        System.out.println("ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Artist a = em.find(Artist.class, id);
        
        if(a != null) {
            
            System.out.println(a);
            System.out.println("");
            System.out.println("First name:");
            String firstname = scanner.nextLine();
        
            System.out.println("Last name:");
            String lastname = scanner.nextLine();
        
            System.out.println("Age:");
            int age = scanner.nextInt();
            scanner.nextLine();
            
            em.getTransaction().begin();
            a.setFirstname(firstname);
            a.setLastname(lastname);
            a.setAge(age);
            em.getTransaction().commit();
            
        }
        else {
            System.out.println("No Artist with that ID...");
        }
        
        em.close();
        
    }

    public static void deleteArtist() {
        
        EntityManager em = emf.createEntityManager();
        System.out.println("ID:");
        int id = scanner.nextInt();
        scanner.nextLine();  
        
        Artist a = em.find(Artist.class, id);
        
        if(a != null) {
            
            em.getTransaction().begin();
            em.remove(a);
            em.getTransaction().commit();
            
        }
        else {
            System.out.println("No Artist with that ID.");
        }
        em.close();
        
    }

    public static void showArtistById() {
        
        EntityManager em = emf.createEntityManager();
        System.out.println("ID:");
        int id = scanner.nextInt();
        scanner.nextLine();  
        
        Artist a = em.find(Artist.class, id);
        
        if(a != null)
            System.out.println(a);
        else
            System.out.println("No Artist with that ID.");
        
        em.close();
        
    }

    public static void showArtistByFirstName() {
        
        EntityManager em = emf.createEntityManager();
        System.out.println("First name:");
        String firstname = scanner.nextLine();
        
        Query query = em.createQuery("SELECT a FROM Artist a WHERE a.firstname=:firstname");
        query.setParameter("firstname", firstname);
        
        List<Artist> artists = query.getResultList();
        
        artists.stream().forEach(System.out::println);
        
        em.close();
        
    }
    
    public static void showArtistByLastName() {  // Vad blir fel här...?
        
        EntityManager em = emf.createEntityManager();
        System.out.println("Last name:");
        String lastname = scanner.nextLine();
        
        Query query = em.createQuery("SELECT a FROM Artist a WHERE a.lastname=:lastname");
        query.setParameter("lastname", lastname);
        
        List<Artist> artists = query.getResultList();
        
        artists.stream().forEach(System.out::println);
        
        em.close();
    }

    public static void showAll() {
        
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT c FROM Artist c");
        
        List<Artist> artists = query.getResultList();
        
        artists.stream().forEach(a -> System.out.println(a));
        
        em.close();
        
    }   
}
