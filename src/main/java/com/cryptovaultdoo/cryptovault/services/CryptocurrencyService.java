package com.cryptovaultdoo.cryptovault.services;

import com.cryptovaultdoo.cryptovault.api.dto.UserCryptocurrencyDto;
import com.cryptovaultdoo.cryptovault.data.entities.Cryptocurrency;
import com.cryptovaultdoo.cryptovault.data.entities.User;
import com.cryptovaultdoo.cryptovault.data.entities.UserCryptocurrency;
import com.cryptovaultdoo.cryptovault.data.repositories.CryptocurrencyRepository;
import com.cryptovaultdoo.cryptovault.data.repositories.UserCryptocurrencyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

@Service
public class CryptocurrencyService {
    Logger log = LoggerFactory.getLogger(CryptocurrencyService.class);

    private final UserDetailsService userService;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final UserCryptocurrencyRepository userCryptocurrencyRepository;

    public CryptocurrencyService(UserDetailsService userService, CryptocurrencyRepository cryptocurrencyRepository, UserCryptocurrencyRepository userCryptocurrencyRepository) {
        this.userService = userService;
        this.cryptocurrencyRepository = cryptocurrencyRepository;
        this.userCryptocurrencyRepository = userCryptocurrencyRepository;
    }

    /**
     * Returns all Cryptocurrencies for current user.
     *
     * @return List of {@link UserCryptocurrencyDto}
     */
    public List<UserCryptocurrencyDto> inspectCryptocurrencies() {
        log.info("Fetching smart contracts...");
        User user = userService.currentUser();

        return user.getCryptocurrencies().stream()
                .map(UserCryptocurrencyDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Used to deposit Cryptocurrency for current user.
     *
     * @param dto {@link UserCryptocurrencyDto}
     */
    @Transactional
    public void depositCryptocurrency(UserCryptocurrencyDto dto) {
        log.info("Deposit {}{}", dto.amount(), dto.code());

        User user = userService.currentUser();

        // find existing cryptocurrency or create and persist new one
        Cryptocurrency cryptocurrency = cryptocurrencyRepository.findByCode(dto.code())
                .orElse(new Cryptocurrency(dto.name(), dto.code()));

        // find existing relation between user and cryptocurrency
        Optional<UserCryptocurrency> existingUserCryptocurrency = userCryptocurrencyRepository
                .findByUserIdAndCryptocurrencyId(user.getId(), cryptocurrency.getId());
        UserCryptocurrency userCryptocurrency;

        if (existingUserCryptocurrency.isPresent()) {
            // increment amount
            userCryptocurrency = existingUserCryptocurrency.get();
            userCryptocurrency.setAmount(userCryptocurrency.getAmount().add(dto.amount()));
        } else {
            userCryptocurrency = dto.toEntity();
        }

        userCryptocurrency.setUser(user);
        userCryptocurrencyRepository.save(userCryptocurrency);
    }
}
