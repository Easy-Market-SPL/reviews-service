package co.edu.javeriana.easymarket.reviewsservice.config;

import co.edu.javeriana.easymarket.reviewsservice.dtos.ReviewDTO;
import co.edu.javeriana.easymarket.reviewsservice.models.Review;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        modelMapper.addMappings(new PropertyMap<Review, ReviewDTO>() {
            @Override
            protected void configure() {
                map(source.getProductCode().getCode(), destination.getProductCode());
            }
        });

        return modelMapper;
    }
}
