package com.trivago.casestudy.entity.key.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationId implements Serializable {
    private Long advertiserId;
    private Long id;

    // hashCode and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccommodationId that = (AccommodationId) o;
        return Objects.equals(advertiserId, that.advertiserId) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advertiserId, id);
    }
}