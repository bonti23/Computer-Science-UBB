using System;
namespace BasketModel
{
    [Serializable]
    public class Entity<ID>
    {
        public ID id { get; set; }

    }
}