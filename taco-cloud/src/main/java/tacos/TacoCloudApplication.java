package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import java.util.Arrays;

@SpringBootApplication
public class TacoCloudApplication {
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo,
                                        UserRepository userRepo, PasswordEncoder encoder, TacoRepository tacoRepo) { // user repo for ease of testing with a built-in user

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Ingredient flourTortilla = saveAnIngredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
                Ingredient cornTortilla = saveAnIngredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP);
                Ingredient groundBeef = saveAnIngredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
                Ingredient carnitas = saveAnIngredient("CARN", "Carnitas", Ingredient.Type.PROTEIN);
                Ingredient tomatoes = saveAnIngredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES);
                Ingredient lettuce = saveAnIngredient("LETC", "Lettuce", Ingredient.Type.VEGGIES);
                Ingredient cheddar = saveAnIngredient("CHED", "Cheddar", Ingredient.Type.CHEESE);
                Ingredient jack = saveAnIngredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE);
                Ingredient salsa = saveAnIngredient("SLSA", "Salsa", Ingredient.Type.SAUCE);
                Ingredient sourCream = saveAnIngredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE);

                Taco taco1 = new Taco();
                taco1.setId("TACO1");
                taco1.setName("Carnivore");
                taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
                tacoRepo.save(taco1).subscribe();

                Taco taco2 = new Taco();
                taco2.setId("TACO2");
                taco2.setName("Bovine Bounty");
                taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
                tacoRepo.save(taco2).subscribe();

                Taco taco3 = new Taco();
                taco3.setId("TACO3");
                taco3.setName("Veg-Out");
                taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
                tacoRepo.save(taco3).subscribe();

            }

            private Ingredient saveAnIngredient(String id, String name, Ingredient.Type type) {
                Ingredient ingredient = new Ingredient(id, name, type);
                repo.save(ingredient).subscribe();
                return ingredient;
            }
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }


}
