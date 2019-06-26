package com.sharepool.server.rest.util;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;

public class RestHelperUtil {

    private RestHelperUtil() {
    }

    public static <T, K> T checkExists(CrudRepository<T, K> repository, K id, Class<T> tClass) {
        return checkExists(() -> repository.findById(id), id, tClass);
    }

    public static <T, K> T checkExists(Supplier<Optional<T>> supplier, K id, Class<T> tClass) {
        Optional<T> obj = supplier.get();
        if (!obj.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No %s found for id %s",
                            tClass.getSimpleName(),
                            id.toString()));
        }

        return obj.get();
    }
}
