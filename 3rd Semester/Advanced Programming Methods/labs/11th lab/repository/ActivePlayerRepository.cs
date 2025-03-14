
using NBA_League.domain;

namespace NBA_League.repository;

class ActivePlayerRepository : InFileRepository<Tuple<int, int>, ActivePlayer>
{
    public ActivePlayerRepository(string filename) : base(filename, EntityToFileMapping.CreateActivePlayer) { }
}
