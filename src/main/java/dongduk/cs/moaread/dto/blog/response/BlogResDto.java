package dongduk.cs.moaread.dto.blog.response;

import dongduk.cs.moaread.domain.Blog;
import dongduk.cs.moaread.domain.Category;
import dongduk.cs.moaread.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BlogResDto {
    private Blog blog;
    private List<Category> categoryList;
    private List<Post> postList;
}
