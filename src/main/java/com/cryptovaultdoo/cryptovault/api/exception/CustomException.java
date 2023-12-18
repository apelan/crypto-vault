package com.cryptovaultdoo.cryptovault.api.exception;

import java.sql.Timestamp;

public record CustomException(int status, String message, Timestamp timestamp) {
}
