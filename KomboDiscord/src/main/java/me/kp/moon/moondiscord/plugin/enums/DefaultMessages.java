package me.kp.moon.moondiscord.plugin.enums;

public enum DefaultMessages {

    SEM_PERMISSAO("Unknown command. Type \"/help\" for help."),
    APENAS_PLAYERS("§c[KomboDiscord] Apenas Players podem executar esse comando");

    private final String message;
    DefaultMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
