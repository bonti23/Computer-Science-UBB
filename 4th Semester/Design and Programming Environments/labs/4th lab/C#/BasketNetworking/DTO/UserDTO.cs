namespace BasketNetworking.DTO;
[Serializable]
public class UserDTO : EntityDTO<Int64>
{
    public string name { get; set; }
    public string username { get; set; }
    public string password { get; set; }

    public UserDTO() { }
    public UserDTO(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

}
