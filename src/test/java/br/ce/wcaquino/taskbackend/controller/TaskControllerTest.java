package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@Mock
	private TaskRepo todoRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() {
//		MockitoAnnotations.initMocks(this);
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task task = new Task();
		task.setDueDate(LocalDate.now());
		try {
			controller.save(task);
			Assert.fail("Não deve salvar tarefa sem descrição");
		} catch (ValidationException e) {
			Assert.assertEquals( "Fill the task description", e.getMessage());
			Assert.assertEquals(ValidationException.class, e.getClass());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task task = new Task();
		task.setTask("Descrição");;
		try {
			controller.save(task);
			Assert.fail("Não deve salvar tarefa sem data");
		} catch (ValidationException e) {
			Assert.assertEquals( "Fill the due date", e.getMessage());
			Assert.assertEquals(ValidationException.class, e.getClass());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task task = new Task();
		task.setDueDate(LocalDate.of(2010, 1, 1));
		task.setTask("Descrição");
		try {
			controller.save(task);
			Assert.fail("Não deve salvar tarefa com data passada");
		} catch (ValidationException e) {
			Assert.assertEquals( "Due date must not be in past", e.getMessage());
			Assert.assertEquals(ValidationException.class, e.getClass());
		}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task task = new Task();
		task.setDueDate(LocalDate.now());
		task.setTask("Descrição");
		controller.save(task);
		Mockito.verify(todoRepo).save(task);
	}
}
