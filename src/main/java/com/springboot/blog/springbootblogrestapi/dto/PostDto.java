package com.springboot.blog.springbootblogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private Long id;
    @Schema(
            description = "Blog Post Title"
    )
    //title should not be null or empty
    //title should have at least 2 characters

    @NotEmpty
    @Size(min=2,message = "Post title should have at least 2 characters")
    private String title;
    @Size(min=10,message = "Post description should have at least 10 characters")
    private String description;
    @Schema(
            description = "Blog Post Content"
    )
    //post content should not be null or empty
    @NotEmpty
    private String content;
    private Set<CommentDto> commentDtoSet;
    @Schema(
            description = "Blog Post Category"
    )
    private Long categoryId;
}
