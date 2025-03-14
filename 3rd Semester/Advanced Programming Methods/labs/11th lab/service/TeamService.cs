using NBA_League.domain;
using NBA_League.repository;
using NBA_League.exceptions;
using NBA_League.validator;

namespace NBA_League.service;

class TeamService
{
    private Repository<int,Team> teamRepository;
    private Validator<Team> teamValidator;
    public TeamService(Repository<int,Team>teamRepo,Validator<Team>teamVal)
    {
        this.teamRepository = teamRepo;
        this.teamValidator = teamVal;
    }
    public Team Save(Team entity)
    {
        this.teamValidator.validate(entity);
        return this.teamRepository.Save(entity);
    }
    public Team GetTeamByName(string nume)
    {
        List<Team> teams = this.teamRepository.FindAll().ToList();
        var result = teams.Where(t => t.Name.Equals(nume));
        Team resultTeam = result.FirstOrDefault();
        if (resultTeam == null)
            throw new ServiceException("There is no team named " + nume);
        return resultTeam;
    }
    public IEnumerable<Team>GetAll()
    {
        return this.teamRepository.FindAll().ToList();
    }
}
