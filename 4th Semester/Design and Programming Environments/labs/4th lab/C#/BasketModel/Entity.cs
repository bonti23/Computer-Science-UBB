using System;
namespace BasketModel
{
    public class Entity<ID>
    {
        private ID identitykey;
        public ID get_identitykey() {
            return identitykey;
        }
        public void set_identitykey(ID identitykey) {
            this.identitykey = identitykey;
        }
    }
}
