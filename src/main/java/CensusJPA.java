import entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CensusJPA {
    public static void main(String[] args) {
        // Initialization
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("canadacensus");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        entityManager.getTransaction().begin();

        // 2. Use Entity Manager’s find Method to Display information of geographic Area with ID =10
        System.out.println("Q2 - Display information of geographic Area where ID is 10");
        GeographicAreaEntity geographicareaEntity = entityManager.find(GeographicAreaEntity.class, 10);
        System.out.println(geographicareaEntity.toString());
        System.out.println();

        // 3. Use Entity Manager’s createQuery Method to Display Geographic Area information for the Level 2.
        System.out.println();
        {
            System.out.println("Q3 - Display Geographic Area information for the Level 2");
            CriteriaQuery<GeographicAreaEntity> criteriaQuery = criteriaBuilder.createQuery(GeographicAreaEntity.class);
            Root<GeographicAreaEntity> geographicareaEntityRoot = criteriaQuery.from(GeographicAreaEntity.class);
            Predicate predicate = criteriaBuilder.equal(geographicareaEntityRoot.get("level"), 2);
            criteriaQuery.where(predicate);
            CriteriaQuery<GeographicAreaEntity> whereLevel = criteriaQuery.select(geographicareaEntityRoot);
            TypedQuery<GeographicAreaEntity> whereLevelQuery = entityManager.createQuery(whereLevel);
            whereLevelQuery.getResultList().forEach(geo -> System.out.println(geo.toString()));
        }
        System.out.println();

        // 4. Create a NamedQuery (findallIncome) in TotalIncome Entity Class to retrieve information about the Total Income. Display 10 Records only.
        System.out.println();
        System.out.println("Q4 - Retrieve Total Income Data - Display 10 Records only");
        TypedQuery<TotalIncomeEntity> query2 = entityManager.createNamedQuery("findAllIncome", TotalIncomeEntity.class).setMaxResults(10);
        query2.getResultList().forEach(emp -> System.out.println(emp.toString()));
        System.out.println();

        // 5. Use Entity Manager’s createQuery Method to Display total number of records with 2016 Canada Census.
        // a. One couple census family without other persons in the household
        {
            CriteriaQuery<HouseholdEntity> criteriaQuery = criteriaBuilder.createQuery(HouseholdEntity.class);
            Root<HouseholdEntity> householdEntityRoot = criteriaQuery.from(HouseholdEntity.class);
            Predicate predicate = criteriaBuilder.equal(householdEntityRoot.get("householdType"), 4);
            criteriaQuery.where(predicate);
            CriteriaQuery<HouseholdEntity> where = criteriaQuery.select(householdEntityRoot);
            TypedQuery<HouseholdEntity> whereQuery = entityManager.createQuery(where);
            System.out.printf("\nQ5A - Display total number of records with one couple census family without other persons in the household = %d%n", whereQuery.getResultList().size());
        }
        System.out.println();

        // b. 2 or more members in the household
        {
            CriteriaQuery<HouseholdEntity> criteriaQuery = criteriaBuilder.createQuery(HouseholdEntity.class);
            Root<HouseholdEntity> householdEntityRoot = criteriaQuery.from(HouseholdEntity.class);
            Predicate predicate = criteriaBuilder.equal(householdEntityRoot.get("householdSize"), 3);
            criteriaQuery.where(predicate);
            CriteriaQuery<HouseholdEntity> where = criteriaQuery.select(householdEntityRoot);
            TypedQuery<HouseholdEntity> whereQuery = entityManager.createQuery(where);
            System.out.printf("\nQ5B - Display total number of records with 2 or more members in the household = %d%n", whereQuery.getResultList().size());
        }
        System.out.println();

        // c. At least 1 earner in the household
        {
            CriteriaQuery<HouseholdEntity> criteriaQuery = criteriaBuilder.createQuery(HouseholdEntity.class);
            Root<HouseholdEntity> householdEntityRoot = criteriaQuery.from(HouseholdEntity.class);
            Predicate predicate = criteriaBuilder.equal(householdEntityRoot.get("householdEarners"), 3);
            criteriaQuery.where(predicate);
            CriteriaQuery<HouseholdEntity> where = criteriaQuery.select(householdEntityRoot);
            TypedQuery<HouseholdEntity> whereQuery = entityManager.createQuery(where);
            System.out.printf("\nQ5C - Display total number of records with at least 1 earner in the household = %d%n", whereQuery.getResultList().size());
        }
        System.out.println();

        // d. Total income between $80,000 and $89,900
        {
            CriteriaQuery<HouseholdEntity> criteriaQuery = criteriaBuilder.createQuery(HouseholdEntity.class);
            Root<HouseholdEntity> householdEntityRoot = criteriaQuery.from(HouseholdEntity.class);
            Predicate predicate = criteriaBuilder.equal(householdEntityRoot.get("totalIncome"), 15);
            criteriaQuery.where(predicate);
            CriteriaQuery<HouseholdEntity> where = criteriaQuery.select(householdEntityRoot);
            TypedQuery<HouseholdEntity> whereQuery = entityManager.createQuery(where);
            System.out.printf("\nQ5D - Display total number of records with total income between $80,000 and $89,900 = %d%n", whereQuery.getResultList().size());
        }
        System.out.println();

        // 6. Criteria Query
        // a. Use Multiselect to get Code, Level and Name from Geographic Area Table Display 10 Records only
        {
            CriteriaQuery<GeographicAreaEntity> criteriaQuery = criteriaBuilder.createQuery(GeographicAreaEntity.class);
            Root<GeographicAreaEntity> geographicareaEntityRoot = criteriaQuery.from(GeographicAreaEntity.class);
            criteriaQuery.multiselect(geographicareaEntityRoot.get("code"), geographicareaEntityRoot.get("level"), geographicareaEntityRoot.get("name"));
            CriteriaQuery<GeographicAreaEntity> multiSelect = criteriaQuery.select(geographicareaEntityRoot);
            TypedQuery<GeographicAreaEntity> multiSelectQuery = entityManager.createQuery(multiSelect).setMaxResults(10);
            System.out.println("Q6A - Use Multiselect to get Code, Level and Name from Geographic Area Table Display 10 Records only");
            multiSelectQuery.getResultList().forEach(
                    geo -> System.out.printf("\nCode = %d, Level = %d, Name = %s%n",
                            geo.getCode(),
                            geo.getLevel(),
                            geo.getName())
            );
        }
        System.out.println();

        // b. Display Top 20 combined Age information from Age Table order by Desc
        {
            CriteriaQuery<AgeEntity> criteriaQuery = criteriaBuilder.createQuery(AgeEntity.class);
            Root<AgeEntity> ageEntityRoot = criteriaQuery.from(AgeEntity.class);
            criteriaQuery.orderBy(criteriaBuilder.desc(ageEntityRoot.get("combined")));
            CriteriaQuery<AgeEntity> orderBy = criteriaQuery.select(ageEntityRoot);
            TypedQuery<AgeEntity> orderByQuery = entityManager.createQuery(orderBy).setMaxResults(20);

            System.out.println("Q6B - Display Top 20 combined Age information from Age Table order by Desc");
            orderByQuery.getResultList().forEach(
                    age -> System.out.println("\nCombined = " + age.getCombined())
            );
        }
        System.out.println();

        // c. Use Where Clause to Display information for Geographic Area named ‘Peterborough’
        {
            CriteriaQuery<GeographicAreaEntity> criteriaQuery = criteriaBuilder.createQuery(GeographicAreaEntity.class);
            Root<GeographicAreaEntity> geographicareaEntityRoot = criteriaQuery.from(GeographicAreaEntity.class);
            Predicate predicate = criteriaBuilder.equal(geographicareaEntityRoot.get("name"), "Peterborough");
            criteriaQuery.where(predicate);
            CriteriaQuery<GeographicAreaEntity> where = criteriaQuery.select(geographicareaEntityRoot);
            TypedQuery<GeographicAreaEntity> whereQuery = entityManager.createQuery(where);

            System.out.println("Q6C - Use Where Clause to Display information for Geographic Area named ‘Peterborough’");
            whereQuery.getResultList().forEach(
                    geo -> System.out.println("\n\t" + geo.toString())
            );
        }
        System.out.println();

        // d. Display Total Income Description between id 10 to 20
        {
            CriteriaQuery<TotalIncomeEntity> criteriaQuery = criteriaBuilder.createQuery(TotalIncomeEntity.class);
            Root<TotalIncomeEntity> totalincomeEntityRoot = criteriaQuery.from(TotalIncomeEntity.class);
            Predicate predicate = criteriaBuilder.between(totalincomeEntityRoot.get("id"), 10, 20);
            criteriaQuery.where(predicate);
            CriteriaQuery<TotalIncomeEntity> between = criteriaQuery.select(totalincomeEntityRoot);
            TypedQuery<TotalIncomeEntity> betweenQuery = entityManager.createQuery(between);

            System.out.println("Q6D - Display Total Income Description between id 10 to 20");
            betweenQuery.getResultList().forEach(
                    income -> System.out.println("\t\t" + income.toString())
            );
        }
        System.out.println();

        // e. Use Group by Clause to Display Geographic Area Information group by Level
        {
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<GeographicAreaEntity> geographicareaEntityRoot = criteriaQuery.from(GeographicAreaEntity.class);
            criteriaQuery.multiselect(geographicareaEntityRoot.get("level"), criteriaBuilder.count(geographicareaEntityRoot)).groupBy(geographicareaEntityRoot.get("level"));

            System.out.println("Q6E - Use Group by Clause to Display Geographic Area Information group by Level");
            List<Object[]> list = entityManager.createQuery(criteriaQuery).getResultList();
            for (Object[] obj : list) {
                System.out.println("\nLevel " + obj[0] + " = " + obj[1]);
            }
        }
        System.out.println();

        // Exit
        System.out.println("Thank you!");
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
