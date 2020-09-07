package by.mrbregovich.tacos.data;

import by.mrbregovich.tacos.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
