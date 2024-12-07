package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;

import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {

        // Get the SessionFactory and session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            // Start a transaction
            session.beginTransaction();

            // 1. Insert records manually or use persistent object
            Customer customer1 = new Customer("John Doe", "johndoe@example.com", 30, "New York");
            Customer customer2 = new Customer("Jane Smith", "janesmith@example.com", 25, "Los Angeles");
            Customer customer3 = new Customer("Emily Davis", "emilydavis@example.com", 35, "Chicago");
            session.save(customer1);
            session.save(customer2);
            session.save(customer3);

            // 2. Apply restrictions using Criteria Interface

            // Example 1: Find customers with age = 30
            Criteria criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.eq("age", 30));
            List<Customer> result = criteria.list();
            System.out.println("Customers with age 30:");
            for (Customer customer : result) {
                System.out.println(customer.getName() + " - " + customer.getAge());
            }

            // Example 2: Find customers with age > 25
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.gt("age", 25));
            result = criteria.list();
            System.out.println("\nCustomers with age > 25:");
            for (Customer customer : result) {
                System.out.println(customer.getName() + " - " + customer.getAge());
            }

            // Example 3: Find customers with age between 25 and 35
            criteria = session.createCriteria(Customer.class);
            criteria.add(Restrictions.between("age", 25, 35));
            result = criteria.list();
            System.out.println("\nCustomers with age between 25 and 35:");
            for (Customer customer : result) {
                System.out.println(customer.getName() + " - " + customer.getAge());
            }

            // Commit the transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the session
            session.close();
            HibernateUtil.shutdown();
        }
    }
}
