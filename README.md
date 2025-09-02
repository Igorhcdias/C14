# C14
rojeto de Gerenciamento de Tarefas (linha editada na branch de conflito)

## Como usar

### Build
```bash
mvn clean package
```
###Executar
java -jar target/taskcli-1.0.0.jar --help

###Exemplos
# adicionar
java -jar target/taskcli-1.0.0.jar add "Estudar Java" --due 2025-09-10

# listar
java -jar target/taskcli-1.0.0.jar list --pending

# concluir
java -jar target/taskcli-1.0.0.jar done <ID>

# remover
java -jar target/taskcli-1.0.0.jar remove <ID>
