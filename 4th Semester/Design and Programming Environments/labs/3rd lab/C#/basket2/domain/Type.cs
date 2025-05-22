namespace basket2.domain
{
    public enum Type
    {
        FINAL,
        SEMIFINAL,
        QUARTERFINAL,
        REGULAR,
        PLAYOFF
    }

    public static class TypeExtensions
    {
        public static string ToFriendlyString(this Type type)
        {
            switch (type)
            {
                case Type.FINAL: return "Final";
                case Type.SEMIFINAL: return "Semifinal";
                case Type.QUARTERFINAL: return "Quarterfinal";
                case Type.REGULAR: return "Regular";
                case Type.PLAYOFF: return "Playoff";
                default: return type.ToString();
            }
        }
    }
}