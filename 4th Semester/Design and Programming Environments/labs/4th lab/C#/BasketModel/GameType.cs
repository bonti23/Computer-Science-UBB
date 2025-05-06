using System;

namespace BasketModel
{
    [Serializable]
    public enum GameType
    {
        FINAL,
        SEMIFINAL,
        QUARTERFINAL,
        PLAYOFF,
        REGULAR,
        None
    }

    public static class GameTypeExtensions
    {
        public static string ToFriendlyString(this GameType type)
        {
            switch (type)
            {
                case GameType.FINAL: return "Final";
                case GameType.SEMIFINAL: return "Semifinal";
                case GameType.QUARTERFINAL: return "Quarterfinal";
                case GameType.REGULAR: return "Regular";
                case GameType.PLAYOFF: return "Playoff";
                default: return type.ToString();
            }
        }
    }
}
