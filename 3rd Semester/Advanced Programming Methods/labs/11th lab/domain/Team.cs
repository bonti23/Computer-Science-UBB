namespace NBA_League.domain;

public class Team : Entity<int>
{
    public string Name { get; set; }
    public Team(int id, string nume)
    {
        this.Id = id;
        this.Name = nume;
    }
    public Team() { }
    public override string ToString()
    {
        return this.Id + " | " + this.Name;
    }
}
