package hanium.server.i_luv_book.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ijin
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileImage {

    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public ProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
