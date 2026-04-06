WashFlow - Sistema de Monitoramento de Lava-Jato
O WashFlow é uma solução digital desenvolvida para otimizar o fluxo de trabalho em lava-jatos. O sistema permite o acompanhamento em tempo real de veículos, desde a entrada até a finalização do serviço, substituindo controles manuais por um dashboard operacional eficiente.

Funcionalidades (MVP)
Esta versão preliminar foca no Backend e na integridade dos dados, permitindo:

Cadastro de Veículos: Registro de placa e modelo.

Dashboard em Tempo Real: Listagem de todos os veículos no pátio.

Atualização de Status: Fluxo de estados (Aguardando → Lavando → Secagem → Pronto).

Validação de Dados: Filtro de segurança para impedir cadastros inconsistentes.

Tecnologias Utilizadas
Linguagem: Java 26

Banco de Dados: MySQL 8.0

Gerenciamento de Dependências: Maven

Controle de Versão: Git & GitHub

Metodologia de Desenvolvimento
O projeto foi desenvolvido seguindo boas práticas de engenharia de software, utilizando o modelo de Feature Branches e Pull Requests para organização do código:

feature/form: Implementação da lógica de inserção no banco de dados.

feature/lista: Desenvolvimento da visualização do dashboard via terminal.

bugfix/validacao: Correção de falhas na entrada de dados (placas vazias).

feature/status: Implementação da lógica de transição de estados dos veículos.

Como Rodar o Projeto
Banco de Dados:
Execute o script abaixo no seu MySQL para criar a estrutura necessária:
//
SQL
CREATE DATABASE washflow;
USE washflow;
CREATE TABLE status_carros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(10) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'Aguardando'
);
//
Configuração:
Ajuste as credenciais de acesso ao banco no arquivo Conexao.java.

Execução:
Compile e rode a classe App.java através do VS Code ou terminal Maven.
