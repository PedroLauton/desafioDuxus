# Duxus Estágio

Este repositório contém a solução para o desafio de estágio proposto pela Élin Duxus Consultoria.

## Pré-requisitos

O projeto foi desenvolvido utilizando as seguintes tecnologias e ferramentas:

- **Java** como linguagem principal;
- **Maven** como compilador e gerenciador de dependências;
- **Banco de dados H2** (em memória, para simplificar o teste);
- **Spring Tools Suite (STS)** como ambiente de desenvolvimento.

## Clonar o projeto

Para clonar o projeto, basta digitar o seguinte comando no Git Bash: 

```bash
git clone https://github.com/PedroLauton/desafioDuxusEstagio.git
```

## Estrutura do Projeto

As principais classes do projeto são **Integrante**, **Time** e **ComposicaoTime**, conforme especificado no enunciado do desafio. 

O projeto segue uma estruturação em três camadas, com responsabilidades bem definidas:

- **Resource**: Responsável por gerenciar as requisições HTTP da aplicação, atuando como ponto de entrada para as interações com o usuário ou outros serviços. É nesta camada que os endpoints são definidos.
  
- **Service**: Contém a lógica de negócios da aplicação, processando os dados recebidos e aplicando as regras de negócio definidas. Esta camada coordena as operações entre o controlador e o repositório.
  
- **Repository**: Gerencia o acesso aos dados e é responsável pela comunicação com o banco de dados.

Assim, os endpoints estão definidos no pacote `resource`, as regras de negócio no pacote `service`, e a comunicação com o banco de dados no pacote `repositories`.

## Execução do Projeto

Abaixo seguem as instruções de como testar o projeto.

### Acesso ao banco de dados

O projeto utilizou o banco de dados em memória H2. Os dados de configuração do banco de dados encontram-se no pacote `src/main/resource`, nos arquivos **application.properties** e **application-Administrator.properties**. 
Para acessar o banco basta digitar a URL: 

```
https://localhost:8080/db-console
```

Para realizar o login basta colocar os seguintes dados:
- JDBC URL: jdbc:h2:mem:TeamManager
- User Name: Administrator
- Password: adm123

O projeto já conta com times e integrantes pré-cadastrados. Seguindo esses passos, você terá acesso ao banco de dados do sistema. 

### API de Processamento de Dados

Para executar a API, inicie a aplicação Spring Boot e utilize um navegador ou uma ferramenta como o Postman para acessar os endpoints descritos abaixo. Os endpoints podem ser acessados através do endereço `localhost` seguido dos parâmetros.

| Endpoint  | Parâmetros (Chaves) |
|-----------|----------------------|
| `/processamento/TimeDaData` | data=yyyy-MM-dd | 
| `/processamento/IntegranteMaisUsado` | datainicial=yyyy-MM-dd&datafinal=yyyy-MM-dd |
| `/processamento/TimeMaisComum` | datainicial=yyyy-MM-dd&datafinal=yyyy-MM-dd |
| `/processamento/FuncaoMaisComum` | datainicial=yyyy-MM-dd&datafinal=yyyy-MM-dd |
| `/processamento/FranquiaMaisFamosa` | datainicial=yyyy-MM-dd&datafinal=yyyy-MM-dd |
| `/processamento/ContagemPorFranquia` | datainicial=yyyy-MM-dd&datafinal=yyyy-MM-dd |
| `/processamento/ContagemPorFuncao` | datainicial=yyyy-MM-dd&datafinal=yyyy-MM-dd |

Observações:
- Todos os endpoints, exceto **TimeDaData**, permitem valores nulos para datas. Porém, se forem fornecidas datas, é necessário especificar tanto a data inicial quanto a final; caso uma das datas esteja ausente, o sistema retornará uma mensagem de erro.
- O sistema exibe apenas dados relacionados aos times registrados no banco. Integrantes sem associação a um time não serão considerados nos resultados, conforme especificado no desafio.

#### Resultados esperados

ContagemPorFranquia

```
{
    "NBA": 2,
    "Apex": 6
}
```

TimeDaData
```
{
    "Integrantes": [
        "Denis Rodman"
    ],
    "Data": "1993-01-01"
}
```

FuncaoMaisComum
```
{
    "Função": "Sniper"
}
```



### API de Cadastro

Para realizar o cadastro de integrantes ou times, deve-se digitar a seguinte URL no navegador: 

```bash
http://localhost:8080
```
Na página inicial será exibido dois links para realizar os cadastros.

![image](https://github.com/user-attachments/assets/27d74875-ea92-4db5-88ad-7aa16515ebd4)


#### Cadastro de Integrantes

Na página de cadastro de integrantes, preencha os campos necessários e clique em **Cadastrar**. Uma mensagem de confirmação aparecerá caso o cadastro seja bem-sucedido. Caso contrário, uma mensagem de erro será exibida.

![image](https://github.com/user-attachments/assets/9cac63b6-2f4b-45c8-b9b8-654da0ca2e11)

![image](https://github.com/user-attachments/assets/d9618829-6239-4a63-9016-69b1d6e73e9f)

#### Cadastro de Times

Para cadastrar um time, selecione uma data e uma franquia para o time. A escolha da franquia filtra a lista de integrantes disponíveis para seleção, garantindo que todos os integrantes do time pertençam à mesma franquia. Selecione ao menos um integrante e clique em **Cadastrar**. Uma mensagem de confirmação será exibida em caso de sucesso, e mensagens de erro serão exibidas em caso de problemas.

![image](https://github.com/user-attachments/assets/4a70d554-9731-4ab2-9689-0df4f9bb2ded)

![image](https://github.com/user-attachments/assets/95083f72-8aec-496c-812f-86951dc685c6)

## Considerações Finais

O projeto priorizou o desenvolvimento do back-end, com comentários que explicam o funcionamento dos métodos e atributos em áreas mais complexas do código. 

## Contato

Para mais informações, entre em contato:

- GitHub: [Pedro Lauton](https://github.com/PedroLauton)
- Email: [lautonpedro@gmail.com](mailto:lautonpedro@gmail.com)
- LinkedIn: [Pedro Lauton](https://www.linkedin.com/in/pedrolauton)
