export function amount(value) {
  const parts = decimalParts(value);
  if (!parts) return "0.00";
  const padded = parts.digits.padStart(parts.scale + 1, "0");
  const integer = parts.scale ? padded.slice(0, -parts.scale) : padded;
  const fraction = (parts.scale ? padded.slice(-parts.scale) : "").padEnd(2, "0");
  const sign = parts.negative && BigInt(parts.digits) !== 0n ? "-" : "";
  return `${sign}${BigInt(integer).toLocaleString("en-IN")}.${fraction}`;
}

export function sumAmounts(values) {
  const parts = values.map(decimalParts).filter(Boolean);
  const scale = Math.max(2, ...parts.map((part) => part.scale));
  const total = parts.reduce((sum, part) => {
    const value = BigInt(part.digits) * 10n ** BigInt(scale - part.scale);
    return sum + (part.negative ? -value : value);
  }, 0n);
  const negative = total < 0n;
  const digits = (negative ? -total : total).toString().padStart(scale + 1, "0");
  return `${negative ? "-" : ""}${digits.slice(0, -scale)}.${digits.slice(-scale)}`;
}

function decimalParts(value) {
  const match = /^([+-]?)(\d+)(?:\.(\d*))?(?:e([+-]?\d+))?$/i.exec(String(value ?? "0").trim());
  if (!match) return null;

  let digits = `${match[2]}${match[3] || ""}`.replace(/^0+(?=\d)/, "");
  let scale = (match[3] || "").length - Number(match[4] || 0);
  if (scale < 0) {
    digits += "0".repeat(-scale);
    scale = 0;
  }
  return { digits, scale, negative: match[1] === "-" };
}

export function date(value) {
  if (!value) return "-";
  const parsedDate = new Date(`${value}T00:00:00`);
  return Number.isNaN(parsedDate.getTime())
    ? value
    : new Intl.DateTimeFormat("en-IN", {
        day: "2-digit",
        month: "short",
        year: "numeric"
      }).format(parsedDate);
}

export function today() {
  const now = new Date();
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;
}