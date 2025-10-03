package br.edu.ibmec.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("aluno")
@Consumes("application/xml")
@Produces("application/xml")
public class AlunoResource {

	private AlunoService alunoService;

	public AlunoResource() {
		this.alunoService = new AlunoService();
	}

    @GetMapping
	// @Produces(MediaType.APPLICATION_JSON + ", " + MediaType.TEXT_PLAIN)
	// @Produces({"application/json", "text/plain"})
	// @Produces("application/json")
	@Produces( { "application/xml", "application/json"})
	@RequestMapping("{matricula}")
	public Response buscarAluno(@PathParam("matricula") String matricula) {
		try {
			AlunoDTO alunoDTO = alunoService.buscarAluno(new Integer(matricula)
					.intValue());
			Response resposta = Response.ok(alunoDTO).build();
			return resposta;
		} catch (DaoException e) {
			return Response.status(404).build();
		}
	}

	@PostMapping
	public Response cadastrarAluno(AlunoDTO alunoDTO) throws ServiceException,
			DaoException {
		try {
			alunoService.cadastrarAluno(alunoDTO);
			return Response.created(new URI("" + alunoDTO.getMatricula())).build();
		} catch (ServiceException e) {
			if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO)
				return Response.status(400).header("Motivo", "C�digo inv�lido")
						.build();
			if (e.getTipo() == ServiceExceptionEnum.CURSO_NOME_INVALIDO)
				return Response.status(400).header("Motivo", "Nome inv�lido")
						.build();
			else
				return Response.status(400).header("Motivo", e.getMessage())
						.build();
		} catch (DaoException e) {
			return Response.status(400).header("Motivo",
					"Erro no banco de dados").build();
		} catch (URISyntaxException e) {
			throw new RuntimeException();
		}
	}

	@PutMapping
	public Response alterarAluno(AlunoDTO alunoDTO) {
		try {
			alunoService.alterarAluno(alunoDTO);
			return Response.created(new URI("" + alunoDTO.getMatricula())).build();
		} catch (ServiceException e) {
			if (e.getTipo() == ServiceExceptionEnum.CURSO_CODIGO_INVALIDO)
				return Response.status(400).header("Motivo", "C�digo inv�lido")
						.build();
			if (e.getTipo() == ServiceExceptionEnum.CURSO_NOME_INVALIDO)
				return Response.status(400).header("Motivo", "Nome inv�lido")
						.build();
			else
				return Response.status(400).header("Motivo", e.getMessage())
						.build();
		} catch (DaoException e) {
			return Response.status(400).header("Motivo",
					"Erro no banco de dados").build();
		} catch (URISyntaxException e) {
			throw new RuntimeException();
		}
	}

	@DELETE
	@Path("{matricula}")
	public Response removerAluno(@PathParam("matricula") String matricula) {
		try {
			alunoService.removerAluno(new Integer(matricula)
					.intValue());
			Response resposta = Response.ok().build();
			return resposta;
		} catch (DaoException e) {
			return Response.status(404).build();
		}
	}

	@GET
	@Produces("text/plain")
	public String listarAlunos() {
		List<String> nomes = new ArrayList<String>();
		for(Iterator<Aluno> it = alunoService.listarAlunos().iterator(); it.hasNext();)
		{
			Aluno aluno = (Aluno)it.next();
			nomes.add(aluno.getNome());
		} return nomes.toString();
	}
}
