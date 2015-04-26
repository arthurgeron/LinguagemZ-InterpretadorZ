package poo.tarefas.tarefa04;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main04 {

	public static void main(String[] args) throws IOException, URISyntaxException {
		URI uri = Main04.class.getResource("/poo/tarefas/tarefa04/prog1.mz").toURI();

		new InterpretadorMaquinaZ().executar(Files.readAllLines(Paths.get(uri)));
	}

}
