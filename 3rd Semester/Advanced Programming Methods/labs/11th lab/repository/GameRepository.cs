using NBA_League.domain;

namespace NBA_League.repository;
class GameRepository : InFileRepository<int, Game>
{
    public GameRepository(string filename) : base(filename, EntityToFileMapping.CreateGame) { }
}
