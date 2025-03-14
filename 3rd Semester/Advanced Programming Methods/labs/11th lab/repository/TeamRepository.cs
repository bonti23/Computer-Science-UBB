using NBA_League.domain;

namespace NBA_League.repository;

class TeamRepository : InFileRepository<int, Team>
{
    public TeamRepository(string filename) : base(filename, EntityToFileMapping.CreateTeam) { }
}
