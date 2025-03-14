using NBA_League.domain;

namespace NBA_League.repository;

class PlayerRepository : InFileRepository<int, Player>
{
    public PlayerRepository(string filename) : base(filename, EntityToFileMapping.CreatePlayer) { }
}
