using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NBA_League.domain;

public class Entity<TID>
{
    public TID Id { get; set; }
}
