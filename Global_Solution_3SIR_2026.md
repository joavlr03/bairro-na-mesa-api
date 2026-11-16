# Global Solution --- Microservice and Web Engineering & IT Services

**Professor:** Prof. Antonio Carlos de Lima Júnior\
**Turma:** 3SIR\
**Valor:** 8,0 pontos

## 1. Contexto e objetivo

Com base na solução proposta pela equipe para o tema da Global Solution,
deverá ser desenvolvido um microsserviço/API REST utilizando Java com
Spring Boot e persistência em Microsoft SQL Server.

A avaliação contempla desenvolvimento da aplicação, arquitetura em
camadas, Docker, publicação da imagem no Docker Hub, Continuous Delivery
com GitHub Actions e implantação no Microsoft Azure.

-   API REST com operações CRUD, DTOs, persistência no SQL Server,
    versionamento v1 e respostas HTTP adequadas.
-   Conteinerização com Docker, imagem publicada no Docker Hub e
    workflow automatizado de publicação.
-   Aplicação em execução no Azure, documentação OpenAPI/Swagger e
    README de execução.

## 2. Distribuição geral da avaliação

  Critério                                          Peso     Pontos
  ------------------------------------------- ---------- ----------
  Implantação no Azure e SQL Server                  20%       1,60
  Docker Hub                                         15%       1,20
  Continuous Delivery com GitHub Actions             15%       1,20
  Desenvolvimento da API REST e arquitetura          50%       4,00
  **TOTAL**                                     **100%**   **8,00**

## 3. Requisitos e critérios de avaliação

### 3.1. Implantação no Microsoft Azure --- 1,60 ponto

A aplicação Spring Boot deverá ser implantada como container no Azure
App Service for Containers ou Azure Container Apps. O banco deverá ser
Microsoft SQL Server, hospedado no Azure ou em outro ambiente acessível
pela aplicação. A execução exclusivamente local não atende ao requisito.

  -----------------------------------------------------------------------
  Item avaliado                                                    Pontos
  ------------------------------ ----------------------------------------
  Implantar a aplicação                                              0,64
  conteinerizada em um dos       
  serviços aceitos do Azure      

  Configurar corretamente o SQL                                      0,40
  Server e sua conectividade com 
  a aplicação                    

  Disponibilizar endpoints da                                        0,32
  API para acesso externo        

  Comprovar o funcionamento da                                       0,24
  aplicação e persistência de    
  dados                          

  **TOTAL**                                                      **1,60**
  -----------------------------------------------------------------------

### 3.2. Docker Hub --- 1,20 ponto

A equipe deverá construir uma imagem funcional da aplicação e
disponibilizá-la no Docker Hub.

  Item avaliado                                                       Pontos
  --------------------------------------------------------------- ----------
  Criar Dockerfile funcional para a aplicação Spring Boot               0,32
  Construir imagem Docker funcional                                     0,24
  Publicar a imagem em repositório Docker Hub                           0,40
  Disponibilizar nome, tag e instruções para obtenção da imagem         0,24
  **TOTAL**                                                         **1,20**

### 3.3. Continuous Delivery com GitHub Actions --- 1,20 ponto

O workflow deverá ser executado automaticamente após push na branch
principal, compilando a aplicação, construindo a imagem Docker e
publicando-a no Docker Hub. A publicação exclusivamente manual não
atende ao requisito.

  -----------------------------------------------------------------------
  Item avaliado                                                    Pontos
  ------------------------------ ----------------------------------------
  Workflow executado                                                 0,24
  automaticamente após push na   
  branch principal               

  Build automatizado da                                              0,32
  aplicação e da imagem Docker   

  Autenticação e publicação                                          0,40
  automática no Docker Hub       

  Evidenciar execução                                                0,24
  bem-sucedida após alteração no 
  repositório                    

  **TOTAL**                                                      **1,20**
  -----------------------------------------------------------------------

O deploy automático no Azure não é obrigatório. A implantação funcional
no Azure, entretanto, é obrigatória e avaliada separadamente.

### 3.4. Desenvolvimento da API REST e arquitetura --- 4,00 pontos

#### 3.4.1. Entity e Modelagem de Dados --- 0,60 ponto

Implementar quatro entidades coerentes com o tema da Global Solution.

  Item avaliado                                                 Pontos
  --------------------------------------------------------- ----------
  Criar 4 entidades coerentes com o tema                          0,16
  Cada entidade possuir ao menos 5 atributos                      0,12
  Cada entidade possuir ao menos 2 atributos obrigatórios         0,12
  Nomear tabelas no plural                                        0,08
  Relacionamentos coerentes entre as entidades                    0,12
  **TOTAL**                                                   **0,60**

#### 3.4.2. DTO e Mapeamento de Dados --- 1,00 ponto

Utilizar DTOs de entrada e saída, sem expor diretamente entidades nas
requisições e respostas.

  Item avaliado                                               Pontos
  ------------------------------------------------------- ----------
  Criar DTOs de request                                         0,24
  Criar DTOs de response                                        0,24
  Utilizar ModelMapper ou outro framework de mapeamento         0,24
  Separar Entity, Request DTO e Response DTO                    0,28
  **TOTAL**                                                 **1,00**

#### 3.4.3. Controllers, CRUD e respostas HTTP --- 1,20 ponto

Criar uma Controller por entidade, expondo POST (criar), GET (listar),
GET /{id} (consultar), PUT /{id} (atualizar) e DELETE /{id} (excluir),
observando os códigos HTTP apropriados.

  -----------------------------------------------------------------------
  Item avaliado                                                    Pontos
  ------------------------------ ----------------------------------------
  Criar uma Controller para cada                                     0,24
  entidade                       

  Implementar corretamente                                           0,50
  operações CRUD                 

  Respostas HTTP de sucesso                                          0,12
  coerentes com as operações     

  Tratar erros de requisição e                                       0,10
  recursos inexistentes          

  Padronizar corpo/status das                                        0,04
  respostas de erro sem expor    
  detalhes internos              

  Garantir que Controllers não                                       0,20
  acessem diretamente Repository 

  **TOTAL**                                                      **1,20**
  -----------------------------------------------------------------------

Códigos esperados, conforme o caso: `201 Created` para criação; `200 OK`
para consultas e atualizações com conteúdo; `204 No Content` para
exclusão sem corpo; `400 Bad Request` para dados inválidos;
`404 Not Found` para recursos inexistentes. Outros códigos HTTP
tecnicamente adequados poderão ser aceitos, desde que usados de modo
consistente e justificado.

O tratamento de falhas inesperadas deve impedir exposição de stack
traces, senhas ou detalhes internos nas respostas públicas. O uso de
`500 Internal Server Error` não substitui o tratamento de erros de
validação ou de recurso não encontrado.

#### 3.4.4. Arquitetura em Camadas e Persistência --- 0,80 ponto

Separar Controller, Service, Repository, Entity e DTO, mantendo as
regras de negócio na camada Service e o acesso ao SQL Server na camada
Repository.

  Item avaliado                                                       Pontos
  --------------------------------------------------------------- ----------
  Service para regras de negócio e intermediação                        0,28
  Repository para persistência no SQL Server                            0,20
  Separação entre Controller, Service, Repository, Entity e DTO         0,32
  **TOTAL**                                                         **0,80**

#### 3.4.5. Versionamento da API --- 0,10 ponto

Adotar versão 1 nas rotas, por exemplo `GET /api/v1/clientes`.

  Item avaliado                                           Pontos
  --------------------------------------------------- ----------
  Incluir versão 1 nas rotas dos endpoints                  0,06
  Versionamento consistente em todas as Controllers         0,04
  **TOTAL**                                             **0,10**

#### 3.4.6. Documentação OpenAPI/Swagger --- 0,10 ponto

Oferecer documentação interativa dos endpoints; a rota raiz `/` deverá
apresentar a documentação ou redirecionar para ela.

  Item avaliado                                     Pontos
  --------------------------------------------- ----------
  Configurar OpenAPI/Swagger                          0,04
  Disponibilizar documentação a partir de `/`         0,04
  Documentar corretamente os endpoints                0,02
  **TOTAL**                                       **0,10**

#### 3.4.7. README e Instruções de Execução --- 0,20 ponto

  Item avaliado                                        Pontos
  ------------------------------------------------ ----------
  Explicar o objetivo da solução                         0,04
  Documentar execução do Spring Boot                     0,04
  Documentar configuração/execução do SQL Server         0,04
  Informar Docker Hub, workflow e URL no Azure           0,04
  Apresentar instruções para testar a solução            0,04
  **TOTAL**                                          **0,20**

## 4. Distribuição final da nota

     Nº Critério                                 Pontos
  ----- ------------------------------------ ----------
      1 Azure e SQL Server                         1,60
      2 Docker Hub                                 1,20
      3 GitHub Actions                             1,20
    4.1 Entity e Modelagem                         0,60
    4.2 DTOs e Mapeamento                          1,00
    4.3 Controllers, CRUD e respostas HTTP         1,20
    4.4 Arquitetura e Persistência                 0,80
    4.5 Versionamento v1                           0,10
    4.6 OpenAPI/Swagger                            0,10
    4.7 README                                     0,20
        **TOTAL GERAL**                        **8,00**

## 5. Evidências obrigatórias

  Evidência                     Finalidade
  ----------------------------- --------------------------------------------------
  GitHub público                Código-fonte, organização, Dockerfile e workflow
  Docker Hub                    Imagem publicada e tag
  Histórico do GitHub Actions   Execução automatizada após push
  URL da API no Azure           Testes das rotas e respostas HTTP
  SQL Server funcional          Comprovação da persistência
  OpenAPI/Swagger               Consulta e teste dos endpoints
  README.md                     Instruções de execução e validação

## 6. Formato de entrega

A entrega deverá ser realizada no Portal do Aluno, na área Entrega de
Trabalhos, com o envio de um arquivo TXT contendo obrigatoriamente:

-   Nome completo e RM de todos os integrantes.
-   Link do repositório GitHub público com código-fonte e workflow.
-   Link da API implantada no Azure.
-   Link do repositório Docker Hub com a imagem publicada.

### 6.1. Exemplo de arquivo TXT

``` text
GLOBAL SOLUTION - 3SIR
INTEGRANTES
Nome: João da Silva | RM: 123456
Nome: Maria Oliveira | RM: 654321
GITHUB: https://github.com/equipe/projeto
API AZURE: https://projeto.azurewebsites.net
DOCKER HUB: https://hub.docker.com/r/equipe/projeto
```

## 7. Observações importantes

-   O valor máximo da avaliação é 8,0 pontos: 50% para Azure, Docker Hub
    e GitHub Actions e 50% para desenvolvimento da API e arquitetura.
-   A simples existência de Dockerfile ou workflow não comprova seu
    funcionamento; o professor poderá exigir execução e evidências.
-   A API deverá estar acessível durante a avaliação para testes de
    CRUD, respostas HTTP e persistência no SQL Server.
-   O banco SQL Server poderá estar em qualquer ambiente, desde que a
    aplicação implantada no Azure consiga acessá-lo.
-   Não expor credenciais, tokens e outras informações sensíveis no
    repositório público, no README ou nas respostas HTTP.
-   Links indisponíveis ou inválidos poderão comprometer a avaliação dos
    respectivos critérios.
-   **Data de Entrega:** 16/11/26, 23:55

------------------------------------------------------------------------

*Global Solution • Turma 3SIR • Prof. Antonio Carlos de Lima Júnior*
