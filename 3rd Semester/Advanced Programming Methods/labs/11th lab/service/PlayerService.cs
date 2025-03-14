using NBA_League.domain;
using NBA_League.repository;
using NBA_League.validator;
using NBA_League.exceptions;

namespace NBA_League.service;

class PlayerService
{
    private Repository<int,Player> playerRepository;
    private Validator<Player> playerValidator;
    public PlayerService(Repository<int,Player> playerRepo, Validator<Player> playerVal)
    {
        this.playerValidator = playerVal;
        this.playerRepository = playerRepo;
    }
    public IEnumerable<Player>GetAll()
    {
        return this.playerRepository.FindAll().ToList();
    }
    public IEnumerable<Player>GetAllPlayersByTeam(Team team)
    {
        List<Player> players = this.GetAll().ToList();
        var result = players.Where(p => p.Team.Name.Equals(team.Name));
        List<Player> resultPlayers = result.ToList();
        if (resultPlayers.Count == 0)
            throw new ServiceException("The teams does not have players!");
        return resultPlayers;

    }
    public Player GetById(int ID)
    {
        return this.playerRepository.FindOne(ID);
    }
}
