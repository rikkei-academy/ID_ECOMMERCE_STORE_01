package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.PasswordResetToken;
@Repository
public interface ForgotPassRepository extends JpaRepository<PasswordResetToken,Integer> {
    @Query(value = "select id, startDate, token, userid\n" +
            "    from PasswordResetToken\n" +
            "where id= (select max(id)\n" +
            "           from PasswordResetToken\n" +
            "           where userid = :uId)", nativeQuery = true)
    PasswordResetToken getLastTokenByUserId(@Param("uId")int uId);
}
