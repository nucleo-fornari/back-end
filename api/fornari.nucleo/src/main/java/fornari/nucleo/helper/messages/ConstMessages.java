package fornari.nucleo.helper.messages;

public class ConstMessages {
    public static final String INVALID_USER_ROLE = "Função inválida. Os valores permitidos são: RESPONSAVEL, SECRETARIO, PROFESSOR e COORDENADOR.";

    public static final String INVALID_CPF = "CPF inválido. Por favor, verifique se foi digitado corretamente.";

    public static final String INVALID_EMAIL = "Email inválido. Por favor, forneça um email no formato correto.";

    public static final String INVALID_CHAMADO_TIPO_PRIORITY = "Os níveis de prioridade permitidos são: 1, 2 e 3.";

    public static final String NOT_ALLOWED_TO_REGISTER_CHAMADO_WITHOUT_TIPO = "Não é permitido cadastrar chamados sem um tipo.";

    public static final String NOT_FOUND_EMAIL_AND_CPF = "Email ou CPF inválido.";

    public static final String ALREADY_EXISTS_RESTRICAO_BY_TIPO = "Restricao do tipo \"%s\" já existe.";

    public static final String ALREADY_EXISTS_ALUNO_BY_RA = "O RA digitado está em uso.";

    public static final String INVALID_BIRTHDATE_FOR_ALUNO = "Data de nascimento inválida. A idade deve estar entre 0 e 6 anos.";

    public static final String INVALID_KINSHIP = "Os parentescos permitidos são GENITOR, IRMÃO, AVÔ, TIO E PRIMO";

    public static final String PARENT_COUNT_EXCEEDED = "O limite de genitores permitidos é de 2.";

    public static final String NOT_FOUND_RESTRICAO_BY_ID = "Restricao com ID %d não encontrada.";

    public static final String INVALID_UF = "UF inválida.";

    public static final String NOT_FOUND_ALUNO_BY_ID = "Aluno com ID %d não encontrado.";

    public static final String ALREADY_RESGISTERED_RESPONSIBLE_FOR_ALUNO = "O responsável já está registrado.";
}
