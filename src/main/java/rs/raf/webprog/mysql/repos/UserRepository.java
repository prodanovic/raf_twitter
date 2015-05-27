package rs.raf.webprog.mysql.repos;

import org.springframework.data.repository.CrudRepository;
import rs.raf.webprog.mysql.domain.User;

/**
 * Created by srdjanp@ballab.com on 2/13/2015.
 */
public interface UserRepository extends CrudRepository<User, String> {

    @Override
    User findOne(String id);


}
