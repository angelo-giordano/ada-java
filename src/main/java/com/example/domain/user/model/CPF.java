package com.example.domain.user.model;

import java.util.Objects;

/**
 * Value Object para CPF (Cadastro de Pessoa Física)
 * Valida formato e dígitos verificadores
 */
public class CPF {
    
    private final String value;

    public CPF(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        
        // Remove pontos, hífens e espaços
        String cleaned = value.replaceAll("[.\\-\\s]", "");
        
        if (!isValid(cleaned)) {
            throw new IllegalArgumentException("CPF inválido: " + value);
        }
        
        this.value = cleaned;
    }

    private boolean isValid(String cpf) {
        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se não são todos dígitos iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        return true;
    }

    public String getValue() {
        return value;
    }

    public String getFormatted() {
        return value.substring(0, 3) + "." +
               value.substring(3, 6) + "." +
               value.substring(6, 9) + "-" +
               value.substring(9, 11);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CPF)) return false;
        CPF cpf = (CPF) o;
        return Objects.equals(value, cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return getFormatted();
    }
}